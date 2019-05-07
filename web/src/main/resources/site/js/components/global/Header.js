const { NavLink } = window.ReactRouterDOM;

const Header = () => {
    const checkActive = (match, location) => {
    if(!location) return false;
    const {pathname} = location;

    return pathname.indexOf('/shows') !== -1;
  }

  return (
    <header>
      <nav>
        <ul className='navLinks'>
          <li><NavLink to="/shows" isActive={checkActive}>Shows</NavLink></li>
          <li><NavLink to="/upload">Upload Inventory</NavLink></li>
        </ul>
      </nav>
    </header>
  );
};
