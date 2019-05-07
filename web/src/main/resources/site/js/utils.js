function toJson(queryString) {
    let asURI = queryString.replace("?", "").replace(/&/g, "\",\"").replace(/=/g,"\":\"")

    return JSON.parse('{"' + decodeURI(asURI) + '"}')
}

function toQueryString(json) {
    return "?" + Object.keys(json).map(key => key + '=' + json[key]).join('&')
}

function capitalize(str) {
    //cant believe javascript doesnt have this already
    return str.charAt(0).toUpperCase() + str.slice(1)
}

function toIsoDate(date) {
    let iso = date.toISOString()

    return iso.substr(0, iso.indexOf("T"))
}