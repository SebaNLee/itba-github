require_relative 'point'

my_point = Point.new(1.5, 2.5)
puts my_point # Imprime {1.5, 2.5}
puts my_point.distance(Point.new(1.5, 3.0)) # Imprime 0.5
