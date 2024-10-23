require_relative 'format_text'

class ItalicText < FormatText
  ELEMENT = 'i'
  def initialize(content)
    super(content, ELEMENT)
  end
end
