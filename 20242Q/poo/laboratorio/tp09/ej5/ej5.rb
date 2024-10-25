require_relative 'bag'
require_relative 'point'

my_bag = Bag.new
my_bag.add(Point.new(0, 0))
my_bag.add(Point.new(1, 2))
my_bag.add(Point.new(3, 4))
my_bag.add(Point.new(1, 2))
puts my_bag
puts my_bag.size
puts my_bag.count(Point.new(1, 2))
puts my_bag.delete(Point.new(1, 2))
puts my_bag
puts my_bag.delete(Point.new(1, 2))
puts my_bag

# salida esperada:

# {{0, 0}=>1, {1, 2}=>2, {3, 4}=>1}
# 3
# 2
# 1
# {{0, 0}=>1, {1, 2}=>1, {3, 4}=>1}
# 0
# {{0, 0}=>1, {3, 4}=>1}

# !! yo lo hice con valor default 0, a diferencia de las soluciones
# me quedó más corto