<projects>
{
    for $project in doc("projects.xml")//project
    return $project[platform/@name = "PHP" and count(staff/person) < count(//person) div count(//project)]
}
</projects>

(: !! idea genial para el promedio :)