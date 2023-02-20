import 'dart:convert';

import 'package:equatable/equatable.dart';

class RecipeResponse extends Equatable {
  final String id;
  final String name;
  final String description;
  final String category;
  final String img;
  final String author;
  final int prepTime;
  final String createdAt;
  const RecipeResponse({
    required this.id,
    required this.name,
    required this.description,
    required this.category,
    required this.img,
    required this.author,
    required this.prepTime,
    required this.createdAt,
  });

  RecipeResponse copyWith({
    String? id,
    String? name,
    String? description,
    String? category,
    String? img,
    String? author,
    int? prepTime,
    String? createdAt,
  }) {
    return RecipeResponse(
      id: id ?? this.id,
      name: name ?? this.name,
      description: description ?? this.description,
      category: category ?? this.category,
      img: img ?? this.img,
      author: author ?? this.author,
      prepTime: prepTime ?? this.prepTime,
      createdAt: createdAt ?? this.createdAt,
    );
  }

  Map<String, dynamic> toMap() {
    return <String, dynamic>{
      'id': id,
      'name': name,
      'description': description,
      'category': category,
      'img': img,
      'author': author,
      'prepTime': prepTime,
      'createdAt': createdAt,
    };
  }

  factory RecipeResponse.fromMap(Map<String, dynamic> map) {
    return RecipeResponse(
      id: map['id'] as String,
      name: map['name'] as String,
      description: map['description'] as String,
      category: map['category'] as String,
      img: map['img'] as String,
      author: map['author'] as String,
      prepTime: map['prepTime'].toInt() as int,
      createdAt: map['createdAt'] as String,
    );
  }

  String toJson() => json.encode(toMap());

  factory RecipeResponse.fromJson(String source) => RecipeResponse.fromMap(json.decode(source) as Map<String, dynamic>);

  @override
  bool get stringify => true;

  @override
  List<Object> get props {
    return [
      id,
      name,
      description,
      category,
      img,
      author,
      prepTime,
      createdAt,
    ];
  }
}