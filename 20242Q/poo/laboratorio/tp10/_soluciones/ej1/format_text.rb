require_relative 'plain_text'

# Clase para aprovechar el comportamiento compartido entre <b> e <i>
class FormatText < PlainText
  def initialize(content, format)
    super(content)
    @format = format
  end

  def source
    "<#{@format}>#{super}</#{@format}>"
  end
end

