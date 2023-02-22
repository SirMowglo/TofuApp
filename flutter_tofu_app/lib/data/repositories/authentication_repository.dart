import 'dart:convert';

import 'package:flutter_tofu_app/models/register_request.dart';
import 'package:get_it/get_it.dart';
import 'package:injectable/injectable.dart';

import '../../models/login.dart';
import '../../models/user.dart';
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
    LoginRequest loginRequest = LoginRequest(username: username, password: password);
    var jsonResponse = await _client.post(url, jsonEncode(loginRequest));

    return LoginResponse.fromJson(jsonDecode(jsonResponse));
  }

  Future<void> register(String username, String email, String verifyEmail, String password,
      String verifyPassword, String fullname) async {
    String url = "/auth/register";
    RegisterRequest rr = RegisterRequest(
        username: username,
        email: email,
        verifyEmail: verifyEmail,
        password: password,
        verifyPassword: verifyPassword,
        fullname: fullname);

    var jsonResponse = await _client.post(url, rr.toJson());
  }
}
