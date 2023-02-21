import 'package:flutter_tofu_app/models/recipe.dart';

class RecipePageResponse {
  final List<RecipeResponse>? content;
  final bool? last;
  final bool? first;
  final int? totalPages;
  final int? totalElements;

  const RecipePageResponse(
      {this.content,
      this.last,
      this.first,
      this.totalPages,
      this.totalElements});
  RecipePageResponse copyWith(
      {List<RecipeResponse>? content,
      bool? last,
      bool? first,
      int? totalPages,
      int? totalElements}) {
    return RecipePageResponse(
        content: content ?? this.content,
        last: last ?? this.last,
        first: first ?? this.first,
        totalPages: totalPages ?? this.totalPages,
        totalElements: totalElements ?? this.totalElements);
  }

  Map<String, Object?> toJson() {
    return {
      'content':
          content?.map<Map<String, dynamic>>((data) => data.toJson()).toList(),
      'last': last,
      'first': first,
      'totalPages': totalPages,
      'totalElements': totalElements
    };
  }

  static RecipePageResponse fromJson(Map<String, Object?> json) {
    return RecipePageResponse(
        content: json['content'] == null
            ? []
            : (json['content'] as List)
                .map<RecipeResponse>((data) =>
                    RecipeResponse.fromJson(data as Map<String, Object?>))
                .toList(),
        last: json['last'] == null ? null : json['last'] as bool,
        first: json['first'] == null ? null : json['first'] as bool,
        totalPages:
            json['totalPages'] == null ? null : json['totalPages'] as int,
        totalElements: json['totalElements'] == null
            ? null
            : json['totalElements'] as int);
  }

  @override
  String toString() {
    return '''RecipePageResponse(
                content:${content.toString()},
last:$last,
first:$first,
totalPages:$totalPages,
totalElements:$totalElements
    ) ''';
  }

  @override
  bool operator ==(Object other) {
    return other is RecipePageResponse &&
        other.runtimeType == runtimeType &&
        other.content == content &&
        other.last == last &&
        other.first == first &&
        other.totalPages == totalPages &&
        other.totalElements == totalElements;
  }

  @override
  int get hashCode {
    return Object.hash(
        runtimeType, content, last, first, totalPages, totalElements);
  }
}
