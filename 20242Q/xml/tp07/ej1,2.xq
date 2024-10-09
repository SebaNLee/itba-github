
<projects>
{
    for $project in doc("projects.xml")//projects/project
    where $project/staff[count(person) >= 2 and count(person) <=8]
    return $project
}
</projects>