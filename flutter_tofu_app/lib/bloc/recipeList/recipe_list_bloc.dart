import 'package:bloc/bloc.dart';
import 'package:bloc_concurrency/bloc_concurrency.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter_tofu_app/models/recipe.dart';
import 'package:http/http.dart' as http;
import 'package:stream_transform/stream_transform.dart';

import '../../data/repositories/recipe_repository.dart';

part 'recipe_list_event.dart';
part 'recipe_list_state.dart';

const throttleDuration = Duration(milliseconds: 100);

EventTransformer<E> throttleDroppable<E>(Duration duration) {
  return (events, mapper) {
    return droppable<E>().call(events.throttle(duration), mapper);
  };
}

class RecipeListBloc extends Bloc<RecipeListEvent, RecipeListState> {
  final RecipeRepository recipeRepository;
  int numPage = 0;

  RecipeListBloc({required this.recipeRepository})
      : super(const RecipeListState()) {
    on<GetRecipesEvent>(
      _onGetRecipesEvent,
      transformer: throttleDroppable(throttleDuration),
    );
  }

  Future<void> _onGetRecipesEvent(
      GetRecipesEvent event, Emitter<RecipeListState> emit) async {
    if (state.hasReachedMax) {
      return;
    }
    try {
      if (state.status == RecipeListStatus.initial) {
        final recipePage = await recipeRepository.getRecipeList(0);
        return emit(state.copyWith(
          status: RecipeListStatus.success,
          recipeList: recipePage.content,
          hasReachedMax: recipePage.content!.length< 10 ? true :false,
        ));
      }

      numPage++;
      final recipePage = await recipeRepository.getRecipeList(numPage);
      recipePage.content!.length< 10 || recipePage.content!.isEmpty
          ? emit(state.copyWith(hasReachedMax: true))
          : emit(
              state.copyWith(
                status: RecipeListStatus.success,
                recipeList: List.of(state.recipeList)..addAll(recipePage.content!),
                hasReachedMax: false,
              ),
            );
    } catch (e) {
      print(e);
      emit(state.copyWith(status: RecipeListStatus.failure));
    }
  }
}
