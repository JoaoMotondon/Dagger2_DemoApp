# Dagger2_DemoApp
This project shows some issues I found when first used Dagger2 in a real application (and how I solved them)

Basically it shows how to create a component graph with multiple component dependencies (three depth levels) and exposes objects from the parent components to be available on the children components (each level using a different custom scope). To do so, we created a login process that uses a component that lasts only while an user is logged-in. Everything is destroyed when user is logged out. When a new user logs in, a new component is created. It also shows some components that lasts per activities and give them access to the presenter and repository layers so that we can create and delete tasks for each user.

Then, we demonstrate how to use lazy injection by simulating a heavy service initialization (a fake email provider) and finally show how to properly reconstruct a graph (that contains dependencies) in case of app is killed by the system and later restored.

Please, refer to [this article](http://androidahead.com/2017/05/23/dagger2-sharing-some-ideas/) for detailed information.

![Demo](https://cloud.githubusercontent.com/assets/4574670/26336806/f3974396-3f4a-11e7-89cc-b40a63200d07.gif)

# License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details
