part of 'recipe_list_bloc.dart';

enum RecipeListStatus { initial, success, failure }

class RecipeListState extends Equatable {
  const RecipeListState(
      {this.status = RecipeListStatus.initial,
      this.recipeList = const <RecipeResponse>[],
      this.hasReachedMax = false});

  final RecipeListStatus status;
  final List<RecipeResponse> recipeList;
  final bool hasReachedMax;

  @override
  List<Object> get props => [status, recipeList, hasReachedMax];

  @override
  bool get stringify => true;

  RecipeListState copyWith({
    RecipeListStatus? status,
    List<RecipeResponse>? recipeList,
    bool? hasReachedMax,
  }) {
    return RecipeListState(
      status: status ?? this.status,
      recipeList: recipeList ?? this.recipeList,
      hasReachedMax: hasReachedMax ?? this.hasReachedMax,
    );
  }
}
