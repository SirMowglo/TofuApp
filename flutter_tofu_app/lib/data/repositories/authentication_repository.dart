import 'dart:convert';

import 'package:get_it/get_it.dart';
import 'package:injectable/injectable.dart';

import '../../models/login.dart';
import '../client/rest_client.dart';

@Order(-1)
@singleton
class AuthenticationRepository {

  late RestClient _client;

  AuthenticationRepository() {
    _client = GetIt.I.get<RestClient>();
    //_client = RestClient();
  }

  Future<dynamic> doLogin(String username, String password) async {
    String url = "/auth/login";

    var jsonResponse = await _client.post(url, LoginRequest(username: username, password: password));
    return LoginResponse.fromJson(jsonDecode(jsonResponse));

  }





}