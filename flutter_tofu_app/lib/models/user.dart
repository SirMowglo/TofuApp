// ignore_for_file: public_member_api_docs, sort_constructors_first
import 'login.dart';

class User {
  String? id;
  String? username;
  String? avatar;
  String? fullname;

  User({
    this.id,
    this.username,
    this.avatar,
    this.fullname,
  });

  User.fromLoginResponse(LoginResponse response) {
      this.id = response.id;
      this.username = response.username;
      this.avatar = response.avatar;
      this.fullname = response.fullname;
    }
}
class UserResponse extends User {

  UserResponse(id, username, fullname, avatar) : super(id: id, username: username, fullname: fullname, avatar: avatar);

  UserResponse.fromJson(Map<String, dynamic> json) {
  id = json['id'];
  username = json['username'];
  avatar = json['avatar'];
  fullname = json['fullname'];
}
  Map<String, dynamic> toJson() {
  final Map<String, dynamic> data = new Map<String, dynamic>();
  data['id'] = this.id;
  data['username'] = this.username;
  data['avatar'] = this.avatar;
  data['fullname'] = this.fullname;
  return data;
}

}