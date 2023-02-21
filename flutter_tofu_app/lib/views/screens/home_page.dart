import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_tofu_app/data/repositories/recipe_repository.dart';

import '../../bloc/authentication/authentication_bloc.dart';
import '../../bloc/recipeList/recipe_list_bloc.dart';
import '../../config/locator.dart';
import '../../data/services/authentication_service.dart';
import '../../models/user.dart';
import '../widgets/recipe_list_widget.dart';

class HomePage extends StatelessWidget {
  final User user;

  const HomePage({super.key, required this.user});

  @override
  Widget build(BuildContext context) {
    final authBloc = BlocProvider.of<AuthenticationBloc>(context);
    return Scaffold(
      body: SafeArea(
        minimum: const EdgeInsets.all(16),
        child: BlocProvider(
          create: (_) {
            final recipeRepo = new RecipeRepository();
            return RecipeListBloc(recipeRepository: recipeRepo)..add(GetRecipesEvent());
          },
          child: Center(
              child:
                  const RecipeList(),
                  // const SizedBox(
                  //   height: 12,
                  // ),
                  // ElevatedButton(

                  //   child: Text('Logout'),
                  //   onPressed: (){
                  //     authBloc.add(UserLoggedOutEvent());
                  //   },
                  // ),
                
              ),
              ),
        ),
    );
  }
}
