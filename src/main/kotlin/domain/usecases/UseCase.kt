package domain.usecases

interface UseCase<T> {
    fun execute(): T
}