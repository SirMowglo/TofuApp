import 'dart:convert';

import 'package:injectable/injectable.dart';

import '../../config/locator.dart';
import '../../models/user.dart';
import '../client/rest_client.dart';

@Order(-1)
@singleton
class UserRepository {

  late RestAuthenticatedClient _client;

  UserRepository() {
    _client = getIt<RestAuthenticatedClient>();
  }

  Future<dynamic> me() async {
    String url = "/user/me";

    var jsonResponse = await _client.get(url);
    return UserResponse.fromJson(jsonDecode(jsonResponse));

  }
}