class ServerException {
  final String? status;
  final String? message;
  final String? path;
  final int? statusCode;
  final String? date;
  final List<SubErrors>? subErrors;
  const ServerException(
      {this.status,
      this.message,
      this.path,
      this.statusCode,
      this.date,
      this.subErrors});
  ServerException copyWith(
      {String? status,
      String? message,
      String? path,
      int? statusCode,
      String? date,
      List<SubErrors>? subErrors}) {
    return ServerException(
        status: status ?? this.status,
        message: message ?? this.message,
        path: path ?? this.path,
        statusCode: statusCode ?? this.statusCode,
        date: date ?? this.date,
        subErrors: subErrors ?? this.subErrors);
  }

  Map<String, Object?> toJson() {
    return {
      'status': status,
      'message': message,
      'path': path,
      'statusCode': statusCode,
      'date': date,
      'subErrors':
          subErrors?.map<Map<String, dynamic>>((data) => data.toJson()).toList()
    };
  }

  static ServerException fromJson(Map<String, Object?> json) {
    return ServerException(
        status: json['status'] == null ? null : json['status'] as String,
        message: json['message'] == null ? null : json['message'] as String,
        path: json['path'] == null ? null : json['path'] as String,
        statusCode:
            json['statusCode'] == null ? null : json['statusCode'] as int,
        date: json['date'] == null ? null : json['date'] as String,
        subErrors: json['subErrors'] == null
            ? null
            : (json['subErrors'] as List)
                .map<SubErrors>(
                    (data) => SubErrors.fromJson(data as Map<String, Object?>))
                .toList());
  }

  @override
  String toString() {
    return '''ServerException(
                status:$status,
message:$message,
path:$path,
statusCode:$statusCode,
date:$date,
subErrors:${subErrors.toString()}
    ) ''';
  }

  @override
  bool operator ==(Object other) {
    return other is ServerException &&
        other.runtimeType == runtimeType &&
        other.status == status &&
        other.message == message &&
        other.path == path &&
        other.statusCode == statusCode &&
        other.date == date &&
        other.subErrors == subErrors;
  }

  @override
  int get hashCode {
    return Object.hash(
        runtimeType, status, message, path, statusCode, date, subErrors);
  }
}

class SubErrors {
  final String? object;
  final String? message;
  final String? field;
  final String? rejectedValue;
  
  const SubErrors({this.object, this.message, this.field, this.rejectedValue});

  SubErrors copyWith(
      {String? object, String? message, String? field, String? rejectedValue}) {
    return SubErrors(
        object: object ?? this.object,
        message: message ?? this.message,
        field: field ?? this.field,
        rejectedValue: rejectedValue ?? this.rejectedValue);
  }

  Map<String, Object?> toJson() {
    return {
      'object': object,
      'message': message,
      'field': field,
      'rejectedValue': rejectedValue
    };
  }

  static SubErrors fromJson(Map<String, Object?> json) {
    return SubErrors(
        object: json['object'] == null ? null : json['object'] as String,
        message: json['message'] == null ? null : json['message'] as String,
        field: json['field'] == null ? null : json['field'] as String,
        rejectedValue: json['rejectedValue'] == null
            ? null
            : json['rejectedValue'] as String);
  }

  @override
  String toString() {
    return '''SubErrors(
                object:$object,
message:$message,
field:$field,
rejectedValue:$rejectedValue
    ) ''';
  }

  @override
  bool operator ==(Object other) {
    return other is SubErrors &&
        other.runtimeType == runtimeType &&
        other.object == object &&
        other.message == message &&
        other.field == field &&
        other.rejectedValue == rejectedValue;
  }

  @override
  int get hashCode {
    return Object.hash(runtimeType, object, message, field, rejectedValue);
  }
}
