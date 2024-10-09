<platforms>
{
    for $platform in doc("projects.xml")//platform
    return <platform>{$platform/@name}</platform>
}
</platforms>