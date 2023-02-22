import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_tofu_app/data/repositories/recipe_repository.dart';
import 'package:flutter_tofu_app/views/widgets/custom_button_widget.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:google_nav_bar/google_nav_bar.dart';

import '../../bloc/authentication/authentication_bloc.dart';
import '../../bloc/recipeList/recipe_list_bloc.dart';
import '../../models/user.dart';
import '../widgets/recipe_list_widget.dart';

class MainPage extends StatefulWidget {
  final User user;

  const MainPage({super.key, required this.user});

  @override
  State<MainPage> createState() => _MainPageState(user);
}

class _MainPageState extends State<MainPage> {
  int _selectedIndex = 0;
  final User user;

  final List<Widget> _pages = [
    SafeArea(minimum: const EdgeInsets.all(2), child: _HomePage()),
    SafeArea(minimum: const EdgeInsets.all(2), child: _ProfilePage()),
    SafeArea(minimum: const EdgeInsets.all(2), child: _SettingsPage())
  ];

  _MainPageState(this.user);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        centerTitle: true,
        toolbarHeight: 50,
        title: ClipRRect(
          child: Container(
            width: 90,
            height: 40,
            decoration: BoxDecoration(
              color: Color.fromARGB(200, 255, 255, 255),
              borderRadius: BorderRadius.all(Radius.circular(30)),
            ),
            child: Center(
              child: Text(
                'TOFU',
                style: GoogleFonts.montserrat(
                    fontWeight: FontWeight.w900,
                    fontSize: 20,
                    color: Color.fromARGB(255, 155, 214, 100)),
              ),
            ),
          ),
        ),
        backgroundColor: Color.fromARGB(255, 155, 214, 100),
        elevation: 2,
      ),
      backgroundColor: Color.fromARGB(255, 230, 230, 230),
      body: _pages[_selectedIndex],
      bottomNavigationBar: Padding(
        padding: const EdgeInsets.symmetric(horizontal: 10.0),
        child: Container(
          decoration: BoxDecoration(
              color: Color.fromARGB(255, 155, 214, 100),
              border: Border.all(
                color: Color.fromARGB(255, 145, 199, 94),
                width: 1,
              ),
              borderRadius: BorderRadius.only(
                  topLeft: Radius.circular(15), topRight: Radius.circular(15))),
          child: Padding(
            padding: const EdgeInsets.symmetric(vertical: 8, horizontal: 20),
            child: GNav(
                selectedIndex: _selectedIndex,
                onTabChange: _navigateBottomBar,
                backgroundColor: Color.fromARGB(255, 155, 214, 100),
                color: Color.fromARGB(150, 255, 255, 255),
                activeColor: Colors.white,
                gap: 10,
                tabBackgroundColor: Color.fromARGB(120, 255, 255, 255),
                padding: EdgeInsets.all(10),
                tabs: [
                  GButton(
                    icon: Icons.home,
                    text: 'Home',
                  ),
                  GButton(
                    icon: Icons.person,
                    text: 'Profile',
                  ),
                  GButton(icon: Icons.settings, text: 'Settings')
                ]),
          ),
        ),
      ),
    );
  }

  void _navigateBottomBar(int index) {
    setState(() {
      _selectedIndex = index;
    });
  }
}

class _HomePage extends StatelessWidget {
  const _HomePage({super.key});

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (_) {
        final recipeRepo = new RecipeRepository();
        return RecipeListBloc(recipeRepository: recipeRepo)
          ..add(GetRecipesEvent());
      },
      child: const RecipeList(),
    );
  }
}

class _SettingsPage extends StatelessWidget {
  const _SettingsPage({super.key});
  @override
  Widget build(BuildContext context) {
    final authBloc = BlocProvider.of<AuthenticationBloc>(context);
    return Center(
      child: CustomButton(
        height: 50,
        color: Color.fromARGB(255, 223, 94, 77),
        textColor: Colors.white,
        text: 'Logout',
        onTap: () {
          authBloc.add(UserLoggedOutEvent());
        },
      ),
    );
  }
}

class _ProfilePage extends StatelessWidget {
  const _ProfilePage({super.key});

  @override
  Widget build(BuildContext context) {
    return Center(child: const Text("Profile"));
  }
}
