require_relative 'point'

my_point = Point.new(1, 2)
puts my_point == my_point
puts my_point == Point.new(1, 2)
puts my_point != Point.new(3, 4)
puts my_point == 'Hola Mundo'

# basta con implementar solo ==(), teniendo en cuenta compoarar si es solo el mismo objeto Point
# pues != es la negación de ==