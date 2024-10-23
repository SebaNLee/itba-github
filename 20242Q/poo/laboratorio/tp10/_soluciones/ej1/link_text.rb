require_relative 'plain_text'

class LinkText < PlainText
  def initialize(content, link)
    super(content)
    @link = link
  end

  def source
    "<a href:\"#{@link}\">#{super}</a>"
  end
end
