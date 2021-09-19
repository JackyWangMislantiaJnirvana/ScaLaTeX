println(List(1, 2, 3, 4).map(_ + 1))

def perm[A]: Seq[A] => Seq[Seq[A]] = {
  case Seq() => Seq(Nil);
  case xs =>
    for(
      x <- xs;
      rs <- perm(xs diff Seq(x))
    ) yield x+:rs
}
perm("ABC")

val primes: LazyList[Int] = 2 #:: LazyList.from(3).filter{ n => primes.takeWhile(p => p*p <= n).forall(n % _ != 0) }
println(primes.take(5).toList)

List(-1, 2, 3, 4).forall(_ > 0)