import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_tofu_app/bloc/authentication/authentication_bloc.dart';
import 'package:flutter_tofu_app/views/widgets/LoginForm.dart';
import 'package:google_fonts/google_fonts.dart';

class LoginPage extends StatefulWidget {
  const LoginPage({Key? key}) : super(key: key);

  static Route<void> route() {
    return MaterialPageRoute<void>(builder: (_) => const LoginPage());
  }

  @override
  State<StatefulWidget> createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Color.fromARGB(255, 230, 230, 230),
      body: SafeArea(
        child: Center(
          child: SingleChildScrollView(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                //! Tofu logo
                SizedBox(height: 40),
                Icon(
                  Icons.fastfood,
                  size: 150,
                ),
                Text(
                  'TOFU',
                  style: GoogleFonts.montserrat(
                      fontWeight: FontWeight.w900,
                      fontSize: 50,
                      color: Color.fromARGB(255, 41, 41, 41)),
                ),
                SizedBox(height: 60),
                //! Welcome text
                Text(
                  'Welcome!',
                  style: GoogleFonts.montserrat(
                      fontWeight: FontWeight.bold,
                      fontSize: 28,
                      color: Color.fromARGB(255, 41, 41, 41)),
                ),
                SizedBox(height: 5),
                Text(
                  "Let's login to see some good recipes!",
                  style: GoogleFonts.openSans(
                      fontSize: 18,
                      color: Color.fromARGB(255, 114, 114, 114),
                      fontWeight: FontWeight.w300),
                ),
                //! Form
                BlocBuilder<AuthenticationBloc, AuthenticationState>(
                  builder: (context, state) {
                    final authBloc =
                        BlocProvider.of<AuthenticationBloc>(context);
                    if (state is AuthenticationNotAuthenticatedState) {
                      return LoginForm();
                    }
                    if (state is AuthenticationFailureState ||
                        state is SessionExpiredState) {
                      var msg = (state as AuthenticationFailureState).message;
                      return Center(
                          child: Column(
                        mainAxisAlignment: MainAxisAlignment.center,
                        crossAxisAlignment: CrossAxisAlignment.center,
                        children: <Widget>[
                          Text(msg),
                          TextButton(
                            //textColor: Theme.of(context).primaryColor,
                            child: Text('Retry'),
                            onPressed: () {
                              authBloc.add(AppLoadedEvent());
                            },
                          )
                        ],
                      ));
                    }
                    return Center(
                      child: CircularProgressIndicator(),
                    );
                  },
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
