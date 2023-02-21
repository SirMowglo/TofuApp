import 'package:flutter/material.dart';
import 'package:flutter_tofu_app/models/recipe.dart';

class RecipeCard extends StatelessWidget {
  const RecipeCard({super.key, required this.recipe});

  final RecipeResponse recipe;

  @override
  Widget build(BuildContext context) {
    return Material(
      child: ListTile(
          leading: Text('${recipe.id}'),
          title: Text(recipe.name!),
          isThreeLine: true,
          subtitle: Text(recipe.description!),
          dense: true,
        ),
    );
  }
}
