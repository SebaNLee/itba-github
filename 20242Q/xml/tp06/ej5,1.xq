<projects>
{
    for $project in doc("projects.xml")//projects/project
    where every $person in doc("persons.xml")//persons/person[@document = $project/staff/person/@document] satisfies ($person/age > 31)
    return <project>
    {$project/client}
    {$project/dates}
    <staff>
    {doc("persons.xml")//persons/person[@document = $project/staff/person/@document]}
    </staff>
    </project>
}
</projects>