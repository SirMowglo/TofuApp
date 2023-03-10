import 'dart:convert';

import 'package:flutter_tofu_app/data/client/rest_client.dart';
import 'package:flutter_tofu_app/models/recipe_response.dart';
import 'package:flutter_tofu_app/models/recipe_page_response.dart';
import 'package:injectable/injectable.dart';

import '../../config/locator.dart';

@Order(-1)
@singleton
class RecipeRepository {
  late RestAuthenticatedClient _client;

  RecipeRepository() {
    _client = getIt<RestAuthenticatedClient>();
  }

  Future<RecipePageResponse> getRecipeList(int page) async {
    String url = '/recipe/?page=$page';

    var jsonResponse = await _client.get(url);
    RecipePageResponse rpr =
        RecipePageResponse.fromJson(jsonDecode(jsonResponse));

    return rpr;
  }
}
