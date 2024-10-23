video_club = VideoClub.new
# video_club.add_movie('Dumbo', ...............) # Categoría Children
# video_club.add_movie('ET', ...............) # Categoría Standard
# video_club.add_movie('ZZZ', ...............) # Categoría Recent
video_club.add_customer('Juan')
video_club.add_customer('Ana')
video_club.rent('Dumbo', 'Ana', 5)
video_club.rent('ET', 'Ana', 2)
video_club.rent('ET', 'Juan', 3)
puts video_club.resume('Ana')
puts video_club.resume('Juan')
begin
  video_club.rent('ET', 'Pedro', 3)
rescue RuntimeError => e
  puts e.message
end
