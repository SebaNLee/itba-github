require_relative 'multiple_counter'
require_relative 'counter'

double_counter = MultipleCounter.new(2)
puts double_counter # Imprime 0
double_counter.increment # Aumenta en 2
double_counter.increment
puts double_counter # Imprime 4
double_counter.decrement # Disminuye en 2
puts double_counter # Imprime 2
double_counter.count = 10
double_counter.increment
puts double_counter # Imprime 12