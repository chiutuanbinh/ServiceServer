namespace java serviceprofileserver
typedef i32 int
typedef i64 long
struct day {
  1:int date,
  2:int month,
  3:int year,
}
struct profileInfo{
  1:string userName, 
  2:string email, 
  3:string phoneNumber, 
  4:day birthday,
  5:string id 
}

service ProfileService{
  bool setProfile(1:profileInfo profile),
  profileInfo getProfile(1:string id)
}
