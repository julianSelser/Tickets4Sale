const ShowsTable = ({ genreShows }) => {
    let genreName = genreShows.genre

    return <div>
        <h2> {capitalize(genreName)} </h2>
        <table>
            <tr>
                <th>Title</th>
                <th>Tickets Left</th>
                <th>Tickets Available</th>
                <th>Status</th>
                <th>Price</th>
                <th>Actions</th>
            </tr>
            { genreShows.shows.map(show => (
                 <tr>
                    <td>{show.title}</td>
                    <td>{show.ticketsLeft}</td>
                    <td>{show.ticketsAvailable}</td>
                    <td>{show.status}</td>
                    <td>{show.price}</td>
                    <td><a href="#"><NavLink to={"/order/" + show.id + toQueryString(show)}>order</NavLink></a></td>
                </tr>
            ))}
        </table>
    </div>
}