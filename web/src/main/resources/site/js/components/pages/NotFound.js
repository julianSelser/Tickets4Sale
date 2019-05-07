const NotFound = ({ match }) => {
    const {url} = match;

    return (
    <div>
      <h1>Whoops!</h1>
      <p><strong>{url.replace('/','')}</strong> could not be located.</p>
    </div>
    );
};