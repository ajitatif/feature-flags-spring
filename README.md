# Feature Flags in Spring

Could this be a new way to design software? Rather than relying on multi-tier architecture, is it actually more 
maintainable to design software on features?

This small project uses something similar to the delegation pattern, without any coded delegates. Thanks to Spring IoC, 
we are able to selectively initialize components based on configuration properties. This, plus designing the software 
based on behaviour, rather than object hierarchy can enable us to make use of open-closed principle in a better way.  

How many times did we need to change model, view and controller at the same time for a small addition to a feature? 
And how much time did it take for us to code these changes? How many times did we need to resolve conflicts because 
there were other people working in the same code for a totally different feature?  

Long story short, here's what I propose here:
* Put the behaviors in the central of your code structure
* ~~Load all the behaviour selectively by using `@ConditionalOnProperty` annotation of Spring~~
* Load all the behaviour and execute selectively by creating a `Delegate` for each version of the feature 
* Call other behaviors (child-feature or sibling-feature) from within a behavior (a feature) 
* Use services only for external calls

This way, if you need to change a behaviour, you only change the code related to that particular behaviour, nothing else

Anyways, let me know if this helps; and order me a beer when you see me in person. Or rather, order me a beer even if 
this doesn't help anything, I'm always up for a beer.

## To-Do

- [x] Handle A/B testing  
- [ ] Reduce the amount of boilerplate code of creating delegates for each feature. Generify or create proxied on the fly
- [ ] Implement feature usage metrics

## Licence

WTFPL License

Everyone is permitted to copy and distribute verbatim or modified
copies of this license document, and changing it is allowed as long
as the name is changed.

           DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION

0. You just DO WHAT THE FUCK YOU WANT TO.
