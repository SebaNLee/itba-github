a = String.new('Hola Mundo')
b = String.new('Hola Mundo')
puts a == b
puts a === b
puts a.eql? b
puts a.equal? b
puts a <=> b

# salida:
# true
# true
# true
# false
# 0

# == es equivalencia
# equal? es identidad
# === es comparación en case
# eql? es comparación de hash
# <=> es comparación