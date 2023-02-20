import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_tofu_app/views/screens/home_page.dart';

import 'bloc/authentication/authentication_bloc.dart';
import 'config/locator.dart';
import 'data/services/authentication_service.dart';
import 'views/views.dart';

void main() {
  //WidgetsFlutterBinding.ensureInitialized();
  //await SharedPreferences.getInstance();
  setupAsyncDependencies();
  configureDependencies();
  //await getIt.allReady();

  runApp(BlocProvider<AuthenticationBloc>(
    create: (context) {
      //GlobalContext.ctx = context;
      final authService = getIt<JwtAuthenticationService>();
      return AuthenticationBloc(authService)..add(AppLoadedEvent());
    },
    child: TofuApp(),
  ));
}

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
      home: BlocBuilder<AuthenticationBloc, AuthenticationState>(
        builder: (context, state) {
          GlobalContext.ctx = context;
          if (state is AuthenticationAuthenticatedState) {
            // show home page
            return HomePage(
                user: state.user,
                );
          }
          return LoginPage();
        },
      ),
    );
  }
}
