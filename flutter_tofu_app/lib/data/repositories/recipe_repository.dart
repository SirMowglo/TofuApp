import 'dart:convert';

import 'package:flutter_tofu_app/data/client/rest_client.dart';
import 'package:flutter_tofu_app/data/repositories/user_repository.dart';
import 'package:flutter_tofu_app/models/recipe_page.dart';

import '../../config/locator.dart';

class RecipeRepository {
  late RestAuthenticatedClient _client;

  UserRepository() {
    _client = getIt<RestAuthenticatedClient>();
  }

  Future<RecipePageResponse> getRecipeList(int page) async {
    String url = "/recipe?page=${page}";

    var jsonResponse = await _client.get(url);
    return RecipePageResponse.fromJson(jsonDecode(jsonResponse));
  }
}
