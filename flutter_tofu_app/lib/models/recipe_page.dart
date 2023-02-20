import 'dart:convert';

import 'package:equatable/equatable.dart';

import 'recipe.dart';

class RecipePageResponse extends Equatable {
  final List<RecipeResponse> content;
  final bool last;
  final bool first;
  final int totalPages;
  final int totalElements;
  const RecipePageResponse({
    required this.content,
    required this.last,
    required this.first,
    required this.totalPages,
    required this.totalElements,
  });

  RecipePageResponse copyWith({
    List<RecipeResponse>? content,
    bool? last,
    bool? first,
    int? totalPages,
    int? totalElements,
  }) {
    return RecipePageResponse(
      content: content ?? this.content,
      last: last ?? this.last,
      first: first ?? this.first,
      totalPages: totalPages ?? this.totalPages,
      totalElements: totalElements ?? this.totalElements,
    );
  }

  Map<String, dynamic> toMap() {
    return <String, dynamic>{
      'content': content.map((x) => x.toMap()).toList(),
      'last': last,
      'first': first,
      'totalPages': totalPages,
      'totalElements': totalElements,
    };
  }

  factory RecipePageResponse.fromMap(Map<String, dynamic> map) {
    return RecipePageResponse(
      content: List<RecipeResponse>.from((map['content'] as List<int>).map<RecipeResponse>((x) => RecipeResponse.fromMap(x as Map<String,dynamic>),),),
      last: map['last'] as bool,
      first: map['first'] as bool,
      totalPages: map['totalPages'].toInt() as int,
      totalElements: map['totalElements'].toInt() as int,
    );
  }

  String toJson() => json.encode(toMap());

  factory RecipePageResponse.fromJson(String source) => RecipePageResponse.fromMap(json.decode(source) as Map<String, dynamic>);

  @override
  bool get stringify => true;

  @override
  List<Object> get props {
    return [
      content,
      last,
      first,
      totalPages,
      totalElements,
    ];
  }
}