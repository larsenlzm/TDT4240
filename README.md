# TDT4240
Every part-exercise has from exercise 1 has its own branch, task1-task4.  
Exercise 2 is contained in task4+singleton and entitypong. Entitypong is Pong realised in an ECS manner. Exercise 2 step 4 is described in the next chapter.


## Exercise 2 step 4:

**a) For the patterns listed in Step3, which are architectural patterns, and which are design patterns?**

Observer pattern - Is a behavioural pattern and therefore a design pattern  
State pattern - Is a behavioural pattern and therefore a design pattern  
Template Method - Is a behavioural pattern and therefore a design pattern  
Model View Controller - Is a architectural pattern  
Abstract Factory - Is a creational pattern and therefore design pattern  
Entity Component System - Is an architectural pattern  
Pipe and filter - Is an architectural pattern  

**What are the relationships and differences of architectural patterns and design patterns?**

Both are recurring solutions to a recurring problem, but the difference is in the scope of the code. While architectural patterns like Model View Controller deal with most of the code a design pattern like singleton only refers to one part of the code.

**b) How is the pattern you chose realized in your code? (Which class(es) works as the pattern you chose?)**

Pretty much all of the code is refactored to make this pattern. This is an architectural pattern and therefore affects the whole architecture of the program. Our earlier solution had the render function from libgdx handle almost everything, but this is now sorted by our created systems, like GameSystem and RenderSystem.

In the task4+singlton branch you see a implementation of the singleton pattern. in the Stat.java you see a singleton class with the static stat instance that can be accesed but not created again. You can access the instance anywhere cause its public.

**c) Is there any advantages in using this pattern in this program? (What are the advantages/disadvantages?)**

Advantages to using the Entity Component System are that it's easy and fast to set up, not very complex. Also makes it really easy to extend features to the system. It also helps in dividing your classes. Its really fast on runtime and better structured in the memory

Disadvantages to using Entity Component System are that it is not as strict as maybe a mvc pattern would be, which could lead to uncertainty where some code might go. Another one is that after some time developing you could quickly end up with very many small components and that makes it harder for further development.
