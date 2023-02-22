import 'package:flutter/material.dart';
import 'package:flutter_tofu_app/models/recipe_response.dart';

class RecipeCard extends StatelessWidget {
  const RecipeCard({super.key, required this.recipe});
  final RecipeResponse recipe;

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 5),
      child: Card(
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(15.0),
        ),
        clipBehavior: Clip.antiAliasWithSaveLayer,
        elevation: 3.0,
        child: Container(
          height: 120,
          width: double.infinity,
          decoration: BoxDecoration(
              image: DecorationImage(
                  fit: BoxFit.cover,
                  image: NetworkImage(
                      'https://raw.githubusercontent.com/SirMowglo/TofuApp/main/spring_tofu_app/uploads/${recipe.img}'))),
          child: Container(
            decoration: BoxDecoration(
              gradient: RadialGradient(
                  // begin: Alignment.topCenter,
                  // end: Alignment.center,
                  radius: 2,
                  colors: [
                    Color.fromARGB(0, 0, 0, 0),
                    Color.fromARGB(170, 0, 0, 0),
                  ]),
            ),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Padding(
                  padding: const EdgeInsets.only(top: 10, right: 15, left: 15),
                  child: Text(
                    recipe.name!,
                    style: TextStyle(
                      fontSize: 22,
                      fontWeight: FontWeight.bold,
                      color: Color.fromARGB(255, 189, 241, 140),
                      shadows: [
                        Shadow(
                          offset: Offset(1.0, 1.0),
                          blurRadius: 2.0,
                          color: Color.fromARGB(200, 0, 0, 0),
                        )
                      ],
                    ),
                  ),
                ),
                Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 15),
                  child: Text(
                    recipe.category!,
                    style: TextStyle(
                      fontSize: 18,
                      color: Colors.white,
                      shadows: [
                        Shadow(
                          offset: Offset(0.5, 0.5),
                          blurRadius: 2.0,
                          color: Color.fromARGB(200, 0, 0, 0),
                        )
                      ],
                    ),
                  ),
                ),
                SizedBox(
                  height: 20,
                ),
                Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 15),
                  child: Text(
                    'Author: ${recipe.author!}',
                    style: TextStyle(
                      fontSize: 15,
                      color: Color.fromARGB(200, 255, 255, 255),
                      shadows: [
                        Shadow(
                          offset: Offset(1.0, 1.0),
                          blurRadius: 2.0,
                          color: Color.fromARGB(237, 0, 0, 0),
                        )
                      ],
                    ),
                  ),
                ),
              ],
            ),
          ),
        ),
        margin: EdgeInsets.only(left: 20.0, right: 20.0, top: 5.0),
      ),
    );
  }
}
