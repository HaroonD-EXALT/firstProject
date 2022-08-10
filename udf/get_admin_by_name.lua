function get_by_name(stream,  name )
    local function map_name(record)
        return map{PK=record.PK, name=record.name,password=record.password, role=record.role}
    end

    local function get_name(record)
        return record.name == name
    end
    return stream : filter(get_name) : map(map_name)
end