class RecipeResponse {
  final String? id;
  final String? name;
  final String? description;
  final String? category;
  final String? img;
  final String? author;
  final int? prepTime;
  final String? createdAt;
  const RecipeResponse(
      {this.id,
      this.name,
      this.description,
      this.category,
      this.img,
      this.author,
      this.prepTime,
      this.createdAt});
  RecipeResponse copyWith(
      {String? id,
      String? name,
      String? description,
      String? category,
      String? img,
      String? author,
      int? prepTime,
      String? createdAt}) {
    return RecipeResponse(
        id: id ?? this.id,
        name: name ?? this.name,
        description: description ?? this.description,
        category: category ?? this.category,
        img: img ?? this.img,
        author: author ?? this.author,
        prepTime: prepTime ?? this.prepTime,
        createdAt: createdAt ?? this.createdAt);
  }

  Map<String, Object?> toJson() {
    return {
      'id': id,
      'name': name,
      'description': description,
      'category': category,
      'img': img,
      'author': author,
      'prepTime': prepTime,
      'createdAt': createdAt
    };
  }

  static RecipeResponse fromJson(Map<String, Object?> json) {
    return RecipeResponse(
        id: json['id'] == null ? null : json['id'] as String,
        name: json['name'] == null ? null : json['name'] as String,
        description:
            json['description'] == null ? null : json['description'] as String,
        category: json['category'] == null ? null : json['category'] as String,
        img: json['img'] == null ? null : json['img'] as String,
        author: json['author'] == null ? null : json['author'] as String,
        prepTime: json['prepTime'] == null ? null : json['prepTime'] as int,
        createdAt:
            json['createdAt'] == null ? null : json['createdAt'] as String);
  }

  @override
  String toString() {
    return '''RecipeResponse(
                id:$id,
                name:$name,
                description:$description,
                category:$category,
                img:$img,
                author:$author,
                prepTime:$prepTime,
                createdAt:$createdAt
    ) ''';
  }

  @override
  bool operator ==(Object other) {
    return other is RecipeResponse &&
        other.runtimeType == runtimeType &&
        other.id == id &&
        other.name == name &&
        other.description == description &&
        other.category == category &&
        other.img == img &&
        other.author == author &&
        other.prepTime == prepTime &&
        other.createdAt == createdAt;
  }

  @override
  int get hashCode {
    return Object.hash(runtimeType, id, name, description, category, img,
        author, prepTime, createdAt);
  }
}
