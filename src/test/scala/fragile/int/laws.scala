package fragile.int

import fragile.nat._

object PlusLaws {
  implicitly[int[_2] ++ int[_3]                =:=                           int[_5]]
  implicitly[(int[_2] ++ int[_3])              =:=              (int[_3] ++ int[_2])]
  implicitly[int[_3] ++ int[_0]                =:=                           int[_3]]
  implicitly[int[_0] ++ int[_3]                =:=                           int[_3]]
  implicitly[((int[_1] ++ int[_2]) ++ int[_3]) =:= (int[_1] ++ (int[_2] ++ int[_3]))]
}

object MinusLaws {
  implicitly[int[_0] -- int[_0] =:= int[_0]]
  // implicitly[int[_3] -- int[_3] =:= int[_0]]
  // implicitly[int[_3] -- int[_2] =:= int[_1]]
  // implicitly[int[_2] -- int[_3] =:= (int[_0] -- int[_1])]
}
