import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class UnionFind<E>
{
    public ArrayList<HashSet<E>> sets = new ArrayList<HashSet<E>>();

    Set get(int set)
    {
        return sets.get(set);
    }

    int union(int set1, int set2)
    {
        if(set1 == set2)
            return set1;

        Set H1 = get(set1);
        Set H2 = get(set2);

        if(H1.size() > H2.size())
        {
            H1.addAll(H2);
            H2.clear();
            return set1;
        }
        else
        {
            H2.addAll(H1);
            H1.clear();
            return set2;
        }
    }

    int add(E e)
    {
        HashSet<E> H = new HashSet<E>();
        H.add(e);
        sets.add(H);
        return sets.size();
    }

    boolean find(E e1, E e2)
    {
        boolean bothPresent = false;

        for(int i = 0; i < sets.size(); i++)
        {

            Set currentSet = sets.get(i);

            if(currentSet.contains(e1) && currentSet.contains(e2))
            {
                bothPresent = true;
                break;
            }
        }
        return bothPresent;
    }

    Integer find(E e1)
    {
        int index = -1;

        for(int i = 0; i < sets.size(); i++)
        {

            Set currentSet = sets.get(i);

            if(currentSet.contains(e1))
            {
                index = i;
                break;
            }
        }

        if(index >= 0)
            return index;
        else
            return null;
    }

    boolean isEmpty()
    {
        if(sets.size() == 0)
            return true;
        else
            return false;
    }
}
