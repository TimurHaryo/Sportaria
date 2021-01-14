package id.train.sportaria.abstaction

abstract class BaseUseCase<out T> {
    abstract suspend operator fun invoke(): T
}