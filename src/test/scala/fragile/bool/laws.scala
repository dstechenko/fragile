package fragile
package bool

object NotLaws {
  implicitly[![False] =:=  True]
  implicitly[![True]  =:= False]
}

object OrLaws {
  implicitly[True  || True  =:=  True]
  implicitly[True  || False =:=  True]
  implicitly[False || True  =:=  True]
  implicitly[False || False =:= False]
}

object AndLaws {
  implicitly[True  && True  =:=  True]
  implicitly[True  && False =:= False]
  implicitly[False && True  =:= False]
  implicitly[False && False =:= False]
}
