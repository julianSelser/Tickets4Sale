const { NavLink } = window.ReactRouterDOM;

const Header = () => {
    const checkActive = (match, location) => {
    if(!location) return false;
    const {pathname} = location;

    return pathname.indexOf('/shows') !== -1 || pathname.indexOf('/_display/') !== -1;
  }

  return (
    <header>
      <nav>
        <ul className='navLinks'>
          {/* Your home route path would generally just be '/'' */}
          <li><NavLink to="/shows" isActive={checkActive}>Shows</NavLink></li>
          <li><NavLink to="/upload" isActive={checkActive}>Upload Inventory</NavLink></li>
        </ul>
      </nav>
    </header>
  );
};
