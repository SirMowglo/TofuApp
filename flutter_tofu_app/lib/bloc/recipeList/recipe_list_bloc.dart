import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter_tofu_app/models/recipe.dart';
import 'package:http/http.dart' as http;

import '../../data/repositories/recipe_repository.dart';

part 'recipe_list_event.dart';
part 'recipe_list_state.dart';

class RecipeListBloc extends Bloc<RecipeListEvent, RecipeListState> {
  final RecipeRepository recipeRepository;
  int numPage = 0;

  RecipeListBloc({required this.recipeRepository})
      : super(const RecipeListState()) {
    on<GetRecipesEvent>((event, emit) {
      // TODO: implement event handler
    });
  }

  Future<void> _onGetRecipesEvent(
      GetRecipesEvent event, Emitter<RecipeListState> emit) async {
    if (state.hasReachedMax) return;
    try {
      if (state.status == RecipeListStatus.initial) {
        final recipePage = await recipeRepository.getRecipeList(numPage);
        return emit(state.copyWith(
          status: RecipeListStatus.success,
          recipeList: recipePage.content,
          hasReachedMax: false,
        ));
      }

      numPage++;
      final recipePage = await recipeRepository.getRecipeList(numPage);
      emit(recipePage.content.isEmpty
      ? state.copyWith(hasReachedMax: true)
      :state.copyWith(
        status: RecipeListStatus.success,
        recipeList: List.of(state.recipeList)..addAll(recipePage.content),
        hasReachedMax: false,
      )
      );
    } catch (_) {
      emit(state.copyWith(status: RecipeListStatus.failure));
    }
  }
}
