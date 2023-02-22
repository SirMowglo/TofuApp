import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_tofu_app/views/screens/login_page.dart';
import 'package:flutter_tofu_app/views/screens/main_page.dart';
import 'package:flutter_tofu_app/views/screens/register_page.dart';
import 'package:flutter_tofu_app/views/views.dart';

import 'bloc/authentication/authentication_bloc.dart';

class GlobalContext {
  static late BuildContext ctx;
}

class TofuApp extends StatelessWidget {

  static Route route() {
    return MaterialPageRoute<void>(builder: (context) {
      var authBloc = BlocProvider.of<AuthenticationBloc>(context);
      authBloc..add(SessionExpiredEvent());
      return _instance;
    });
  }

  static late TofuApp _instance;

  TofuApp() {
    _instance = this;
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      //* Routes
      initialRoute: '/',
      routes: {
        '/':(context) => SplashPage(),
        '/login':(context) => LoginPage(),
        '/register':(context) => RegisterPage()
      },
      debugShowCheckedModeBanner: false,
      // home: 
    );
  }
}
