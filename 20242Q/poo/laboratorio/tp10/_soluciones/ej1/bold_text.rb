require_relative 'format_text'

class BoldText < FormatText
  ELEMENT = 'b'
  def initialize(content)
    super(content, ELEMENT)
  end
end
