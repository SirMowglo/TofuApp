// To parse this JSON data, do
//
//     final page = pageFromJson(jsonString);

import 'dart:convert';

import 'package:flutter_tofu_app/models/user.dart';

RecipePage pageFromJson(String str) => RecipePage.fromJson(json.decode(str));

String pageToJson(RecipePage data) => json.encode(data.toJson());

class RecipePage {
    List<RecipeResponse> content;
    bool last;
    bool first;
    int totalPages;
    int totalElements;

    RecipePage({
        required this.content,
        required this.last,
        required this.first,
        required this.totalPages,
        required this.totalElements,
    });

    RecipePage copyWith({
        List<RecipeResponse>? content,
        bool? last,
        bool? first,
        int? totalPages,
        int? totalElements,
    }) => 
        RecipePage(
            content: content ?? this.content,
            last: last ?? this.last,
            first: first ?? this.first,
            totalPages: totalPages ?? this.totalPages,
            totalElements: totalElements ?? this.totalElements,
        );

    factory RecipePage.fromJson(Map<String, dynamic> json) => RecipePage(
        content: List<RecipeResponse>.from(json["content"].map((x) => RecipeResponse.fromJson(x))),
        last: json["last"],
        first: json["first"],
        totalPages: json["totalPages"],
        totalElements: json["totalElements"],
    );

    Map<String, dynamic> toJson() => {
        "content": List<dynamic>.from(content.map((x) => x.toJson())),
        "last": last,
        "first": first,
        "totalPages": totalPages,
        "totalElements": totalElements,
    };
}

class RecipeResponse {
    String id;
    String name;
    String img;
    String type;
    int prepTime;
    UserResponse author;
    List<Category> categories;
    String createdAt;
    int nlikes;

    RecipeResponse({
        required this.id,
        required this.name,
        required this.img,
        required this.type,
        required this.prepTime,
        required this.author,
        required this.categories,
        required this.createdAt,
        required this.nlikes,
    });

    RecipeResponse copyWith({
        String? id,
        String? name,
        String? img,
        String? type,
        int? prepTime,
        UserResponse? author,
        List<Category>? categories,
        String? createdAt,
        int? nlikes,
    }) => 
        RecipeResponse(
            id: id ?? this.id,
            name: name ?? this.name,
            img: img ?? this.img,
            type: type ?? this.type,
            prepTime: prepTime ?? this.prepTime,
            author: author ?? this.author,
            categories: categories ?? this.categories,
            createdAt: createdAt ?? this.createdAt,
            nlikes: nlikes ?? this.nlikes,
        );

    factory RecipeResponse.fromJson(Map<String, dynamic> json) => RecipeResponse(
        id: json["id"],
        name: json["name"],
        img: json["img"],
        type: json["type"],
        prepTime: json["prepTime"],
        author: UserResponse.fromJson(json["author"]),
        categories: List<Category>.from(json["categories"].map((x) => Category.fromJson(x))),
        createdAt: json["createdAt"],
        nlikes: json["nlikes"],
    );

    Map<String, dynamic> toJson() => {
        "id": id,
        "name": name,
        "img": img,
        "type": type,
        "prepTime": prepTime,
        "author": author.toJson(),
        "categories": List<dynamic>.from(categories.map((x) => x.toJson())),
        "createdAt": createdAt,
        "nlikes": nlikes,
    };
}
class Category {
    String id;
    String name;
    String color;

    Category({
        required this.id,
        required this.name,
        required this.color,
    });

    Category copyWith({
        String? id,
        String? name,
        String? color,
    }) => 
        Category(
            id: id ?? this.id,
            name: name ?? this.name,
            color: color ?? this.color,
        );

    factory Category.fromJson(Map<String, dynamic> json) => Category(
        id: json["id"],
        name: json["name"],
        color: json["color"],
    );

    Map<String, dynamic> toJson() => {
        "id": id,
        "name": name,
        "color": color,
    };
}
