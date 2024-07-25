# Feature Flags in Spring

Could this be a new way to design software? Rather than relying on multi-tier architecture, is it actually more 
maintainable to design software on features?

This small project uses something similar to the delegation pattern. This, plus designing the software 
based on behaviour, rather than object hierarchy can enable us to make use of open-closed principle in a better way.  

How many times did we need to change model, view and controller at the same time for a small addition to a feature? 
And how much time did it take for us to code these changes? How many times did we need to resolve conflicts because 
there were other people working in the same code for a totally different feature?  

Long story short, here's what I propose here:
* Put the behaviors in the focus of your code structure
* ~~Load all the behaviour selectively by using `@ConditionalOnProperty` annotation of Spring~~
* Load all the behaviour and execute selectively by creating a `Delegate` for each version of the feature 
* Call other behaviors (child-feature or sibling-feature) from within a behavior (a feature) 
* Use services only for external calls

This way, if you need to change a behaviour, you only change the code related to that particular behaviour, nothing else

Anyways, let me know if this helps; and order me a beer when you see me in person. Or rather, order me a beer even if 
this doesn't help anything, I'm always up for a beer.

## To-Do

- [x] Handle A/B testing  
- [x] Implement feature usage metrics
- [ ] Make the feature experimentation abstract, and implement config file based experimentation
- [ ] Implement database based experimentation, and invent an easy way of using one or the other
- [ ] Reduce the amount of boilerplate code of creating delegates for each feature. Generify or create proxied on the fly

## How to build and include in your project

The source codes have example features for testing and demonstration purposes. However, these example classes are 
excluded in the built JAR file. In order to build the JAR file:

```bash
./gradlew build
```

or, to publish to your local M2 repository:

```bash
./gradlew publishToMavenLocal
```

This will test and build the library jar. Then you can deploy it to your artifact repository

## How to use

### 1. Add your config

`experiment` configuration is mandatory, with at least one list item in `versions`. also, `strategy` is mandatory
```yaml
...
my-feature:
  experiment:
    strategy: random-check
    versions:
      - threshold: 0.5
        delegate: controlGroupMyFeature
      - threshold: 1
        delegate: experimentGroupMyFeature
  some-other-config: false
  another-nested-config-for-my-feature:
    nested-config-one: 1
    nested-config-two: maybe
...
```

### 2. Add boilerplate code

Sorry, but this is required for the time being. You will need:
1. Feature interface, defining the feature contract - `extends Feature`
2. Feature configuration class, `implements ExperimentedFeatureConfiguration`
3. Disabled feature implementation, `implements NewFeatureInterface`
4. Enabled feature implementation, `implements NewFeatureInterface`
5. Feature delegate `extends FeatureDelegateBase<NewFeatureInterface>`, `implements NewFeatureInterface` and depends on `FeatureDelegationManager`

#### Feature interface
```java
public interface NewFeature extends Feature {
    void publicMethod(Long param);
}
```

#### Feature configuration
```java
@Configuration
@ConfigurationProperties(prefix = "my.new-feature")
public class NewFeatureConfiguration implements ExperimentedFeatureConfiguration {

    private ExperimentConfiguration experiment;

    public void setExperiment(ExperimentConfiguration experiment) {
        this.experiment = experiment;
    }

    @Override
    public ExperimentConfiguration getExperiment() {
        return experiment;
    }
...
```

The trouble here is, you have to add the getters and setters because this is a @Configuration class

#### Disabled and enabled feature implementation
```java
@ExperimentedFeature // implies @Component
public class DisabledMyFeature implements MyFeature {

    @Override
    public void publicMethod(Long param) {
        log.debug("feature disabled for param {}", param);
    }
}

@ExperimentedFeature
public class EnabledMyFeature implements MyFeature {

    @Override
    public void publicMethod(Long param) {
        // work your magic
    }
}
```

#### Delegate

The delegate is the implementation of the feature which is in most cases the `@Primary` Spring bean. 
There is a `FeatureDelegateBase` class which makes use of the `FeatureDelegationManager` to pick the 
correct feature implementation.

```java
@Component
@Primary // important, if you want your application to be implementation-agnostic
public class DelegateMyFeature extends FeatureDelegatBase<MyFeature> implements MyFeature{
    
    public DelegateMyFeature(MyFeatureConfiguration configuration, FeatureDelegationManager FeatureDelegationManager) {
        super(MyFeature.class, configuration, FeatureDelegationManager);
    }
    
    public void publicMethod(Long param) {
        getDelegateToRun(param).publicMethod(param);
    }
}
```

Things to note here:
* Delegate is `@Primary`, so whenever a `MyFeature` is autowired without a bean name, it's the delegate.
* `FeatureDelegationManager#getDelegateToRun()` call requires all the parameters being passed on to the `publicMethod`, because the strategy can 
use any or all of these parameters to decide.   

I hate boilerplate as well, I'll try to get rid of this; I promise

### 3. Write your tests

You can skip this if you want to take risks on production

### 4. Enjoy your feature flag

```java
private MyFeature feature; // add it to Spring component's constructor, or @Autowire

...

public void doStuff(Long param) {
    feature.publicMethod(param); // finally, that's it!
}
```

## How to add more implementations of the same feature

Any `@ExperimentedFeature` is bound as a Spring bean with the default bean naming. This means if you create a new 
feature implementation with name `GenAlphaMyFeature`, then the name you'll be using in the experiment config would be 
`genAlphaMyFeature`.   

I swear there is nothing else to do in order to extend the feature, it'll be picked up by `FeatureDelegationManager` 
during runtime.

## How to add new strategy

* Implement the interface `ExperimentStrategyCheck` 
* Implement method `public double get(Object... params)` depending on your strategy
* Mark the class `@Component`

The point to note here is that the result of `get` method will be compared to each of the versions defined 
in configuration, and whichever implementation has the lowest threshold higher than this result will be run.  
 e.g. for ranges
```text
0.0 -> Feature Implementation A
0.5 -> Feature Implementation B
```
a check of `0.49` will result in running `Feature Implementation A`, whereas a check of `0.5` will result in running 
`Feature Implementation B`

## Licence

WTFPL License

Everyone is permitted to copy and distribute verbatim or modified
copies of this license document, and changing it is allowed as long
as the name is changed.

           DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION

0. You just DO WHAT THE FUCK YOU WANT TO.
