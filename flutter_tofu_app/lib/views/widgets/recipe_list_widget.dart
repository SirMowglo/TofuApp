import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_tofu_app/bloc/recipeList/recipe_list_bloc.dart';
import 'package:flutter_tofu_app/views/widgets/recipe_card_widget.dart';

class RecipeList extends StatefulWidget {
  const RecipeList({super.key});

  @override
  State<RecipeList> createState() => _RecipeListState();
}

class _RecipeListState extends State<RecipeList> {
  final scrollController = ScrollController();

  //* Metodos de estado
  @override
  void initState() {
    super.initState();

    scrollController.addListener(_onScroll);
  }

  @override
  void dispose() {
    scrollController
      ..removeListener(_onScroll)
      ..dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return BlocBuilder<RecipeListBloc, RecipeListState>(
        builder: (context, state) {
      switch (state.status) {
        //! Estado de fallo
        case RecipeListStatus.failure:
          return const Center(
            child: const Text('Failed to get recipes'),
          );

        //! Estado de exito
        case RecipeListStatus.success:
          if (state.recipeList.isEmpty ) {
            return const Center(
              child: Text('No recipes'),
            );
          }
          return ListView.builder(
            itemBuilder: (BuildContext context, int index) {
              return index >= state.recipeList.length
                  ? Center(
                      child: Container(
                          width: 3,
                          height: 3,
                          decoration: BoxDecoration(
                            color: Colors.grey,
                            borderRadius: BorderRadius.all(Radius.circular(3))
                          ),
                      ),
                    )
                  : RecipeCard(recipe: state.recipeList[index]);
            },
            itemCount: state.hasReachedMax
                ? state.recipeList.length
                : state.recipeList.length + 1,
            controller: scrollController,
          );

        //! Estado inicial
        case RecipeListStatus.initial:
          return const Center(
            child: CircularProgressIndicator(),
          );
      }
    });
  }

  //* Otros metodos
  void _onScroll() {
    if (_isBottom) context.read<RecipeListBloc>().add(GetRecipesEvent());
  }

  bool get _isBottom {
    if (!scrollController.hasClients) return false;
    final maxScroll = scrollController.position.maxScrollExtent;
    final currentScroll = scrollController.offset;
    return currentScroll >= (maxScroll * 0.8);
  }
}
