<projects>
{
    for $project in doc("projects.xml")//projects/project
    where $project/platform/@name = "Java"
    order by $project/@name
    return $project
}
</projects>