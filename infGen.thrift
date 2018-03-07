namespace java serviceprofileserver
typedef i32 int
typedef i64 long
struct Day {
  1:int date,
  2:int month,
  3:int year,
}
struct ProfileInfo{
  1:string userName, 
  2:string email, 
  3:string phoneNumber, 
  4:Day birthday,
  5:string id 
}

service ProfileService{
  bool setProfile(1:ProfileInfo profile),
  ProfileInfo getProfile(1:string id),
  bool updateProfile(1:ProfileInfo profile),
  bool removeProfile(1:string id)
}
